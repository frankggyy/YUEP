package com.yuep.core.db.spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

/**
 * Subclass of Spring's standard LocalSessionFactoryBean for Hibernate,
 * supporting JDK 1.5+ annotation metadata for mappings.
 * 
 * <p>
 * Note: This class requires Hibernate 3.2 or higher, with the Java Persistence
 * API and the Hibernate Annotations add-on present.
 * 
 * <p>
 * Example for an AnnotationSessionFactoryBean bean definition:
 * 
 * <pre class="code"> &lt;bean id="sessionFactory" class=
 * "org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean"
 * &gt; &lt;property name="dataSource" ref="dataSource"/&gt; &lt;property
 * name="annotatedClasses"&gt; &lt;list&gt;
 * &lt;value&gt;test.package.Foo&lt;/value&gt;
 * &lt;value&gt;test.package.Bar&lt;/value&gt; &lt;/list&gt; &lt;/property&gt;
 * &lt;/bean&gt;</pre>
 * 
 * Or when using classpath scanning for autodetection of entity classes:
 * 
 * <pre class="code"> &lt;bean id="sessionFactory" class=
 * "org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean"
 * &gt; &lt;property name="dataSource" ref="dataSource"/&gt; &lt;property
 * name="packagesToScan" value="test.package"/&gt; &lt;/bean&gt;</pre>
 * 
 * @author Juergen Hoeller
 * @since 1.2.2
 * @see #setDataSource
 * @see #setHibernateProperties
 * @see #setAnnotatedClasses
 * @see #setAnnotatedPackages
 */
public class AnnotationSessionFactoryBean extends LocalSessionFactoryBean
        implements ResourceLoaderAware {
    private static final String RESOURCE_PATTERN = "/**/*.class";

    private Class<?>[] annotatedClasses;

    private String[] annotatedPackages;

    private String[] packagesToScan;
    
    /** ����annnotation�Զ�����֮�⣬����Ľ���sql�ű�λ�á�
     * <li>������ʱ�����ǻ��Դ����ݿ�ͱ���ʱ����Ҫ���ò�����
     * <li>��debugʱ����Ҫ���øò�����
     */
    private String   sqlLocation;
    
    /** �Ƿ��һ�����У���һ��������Ҫ���� */
    private boolean  firstRun=false;
    
    private TypeFilter[] entityTypeFilters = new TypeFilter[] {
            new AnnotationTypeFilter(Entity.class, false),
            new AnnotationTypeFilter(Embeddable.class, false),
            new AnnotationTypeFilter(MappedSuperclass.class, false) };

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    public AnnotationSessionFactoryBean() {
        setConfigurationClass(AnnotationConfiguration.class);
    }
    
    public void setSqlLocation(String sqlLocation){
        this.sqlLocation=sqlLocation;
    }

    /**
     * Check whether any of the configured entity type filters matches the
     * current class descriptor contained in the metadata reader.
     */
    private boolean matchesFilter(MetadataReader reader,
            MetadataReaderFactory readerFactory) throws IOException {
        if (entityTypeFilters != null) {
            for (TypeFilter filter : entityTypeFilters) {
                if (filter.match(reader, readerFactory)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * To be implemented by subclasses that want to to perform custom
     * post-processing of the AnnotationConfiguration object after this
     * FactoryBean performed its default initialization.
     * 
     * @param config
     *            the current AnnotationConfiguration object
     * @throws HibernateException
     *             in case of Hibernate initialization errors
     */
    protected void postProcessAnnotationConfiguration(
            AnnotationConfiguration config) throws HibernateException {
        
        // �ж��Ƿ��ǵ�һ������
        if(StringUtils.isNotEmpty(sqlLocation)){
            JdbcTemplate template=new JdbcTemplate(getDataSource());
            try{
                template.execute("select count(*) from role");
            }catch(DataAccessException ex){
                // ������쳣��˵��role��û�У�Ҳ����˵��û�п�ʼ����
                System.err.println(ex.getMessage()+",test if table [role] is exist or not");
                this.firstRun=true;
                createTbStartTime=System.currentTimeMillis();
            }
        }
    }
    
    /**
     * ��ʼ�������ݿ���ʱ��
     */
    private long createTbStartTime=0; 
    
    @Override
    protected void afterSessionFactoryCreation() throws Exception {
        super.afterSessionFactoryCreation();
        if(createTbStartTime!=0){
            long t2=System.currentTimeMillis();
            System.out.println("afterSessionFactoryCreation cost "+(t2-createTbStartTime)+" ms");
        }
        
        // �Զ�����󣬻���Ҫִ�ж��������sql�ű�
        executeUpdateSchema();
    }
    
    /**
     * ִ�ж����sql
     * @throws DataAccessException
     */
    private void executeUpdateSchema() throws DataAccessException {
        if(!firstRun)
            return;
        if(StringUtils.isEmpty(sqlLocation))
            return;
        
        // --1,���ļ��ж�ȡsql�ű������뵽list��
        final List<String> sqls=new ArrayList<String>();
        try {
            URL sqlUrl = ResourceUtils.getURL(sqlLocation);
            UrlResource url=new UrlResource(sqlUrl);
            
            InputStreamReader ir = new InputStreamReader(url.getInputStream());
            BufferedReader bd = new BufferedReader(ir);
            String line = null;
            while((line=bd.readLine())!=null){
                String sqlLine=line.trim();
                if(sqlLine.equals("") || sqlLine.startsWith("#"))
                    continue;
                sqls.add(sqlLine);
            }
            bd.close();
            ir.close();
        } catch (Exception e) {
            System.err.println(e.getMessage()+",sqlLocation="+sqlLocation);
            return;
        }
        
        if(sqls.size()<=0)
            return;
        
        // --2,����ִ��sql�����л�#ע���к���
        HibernateTemplate hibernateTemplate = new HibernateTemplate(getSessionFactory());
        hibernateTemplate.setFlushMode(HibernateTemplate.FLUSH_NEVER);
        hibernateTemplate.execute(
            new HibernateCallback() {
                @SuppressWarnings("deprecation")
                @Override
                public Object doInHibernate(Session session)
                        throws HibernateException, SQLException {
                    Connection con = session.connection();
                    String[] sql=sqls.toArray(new String[0]);
                    executeSchemaScript(con, sql);//ִ��sql�ű�
                    return null;
                }
            }
        );
        
        System.out.println("init database finished!");
    }

    /**
     * Delegates to {@link #postProcessAnnotationConfiguration}.
     */
    @Override
    protected final void postProcessConfiguration(Configuration config)
            throws HibernateException {
        postProcessAnnotationConfiguration((AnnotationConfiguration) config);
    }

    /**
     * Reads metadata from annotated classes and packages into the
     * AnnotationConfiguration instance.
     */
    @Override
    protected void postProcessMappings(Configuration config)
            throws HibernateException {
        AnnotationConfiguration annConfig = (AnnotationConfiguration) config;
        if (annotatedClasses != null) {
            for (Class<?> annotatedClasse : annotatedClasses) {
                annConfig.addAnnotatedClass(annotatedClasse);
            }
        }
        if (annotatedPackages != null) {
            for (String annotatedPackage : annotatedPackages) {
                annConfig.addPackage(annotatedPackage);
            }
        }
        scanPackages(annConfig);
    }

    /**
     * Perform Spring-based scanning for entity classes.
     * 
     * @see #setPackagesToScan
     */
    protected void scanPackages(AnnotationConfiguration config) {
        if (packagesToScan != null) {
            for (String pkg : packagesToScan) {
                try {
                    String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                            + ClassUtils.convertClassNameToResourcePath(pkg)
                            + RESOURCE_PATTERN;
                    Resource[] resources = resourcePatternResolver
                            .getResources(pattern);
                    MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(
                            resourcePatternResolver);
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            MetadataReader reader = readerFactory
                                    .getMetadataReader(resource);
                            String className = reader.getClassMetadata()
                                    .getClassName();
                            if (matchesFilter(reader, readerFactory)) {
                                config
                                        .addAnnotatedClass(resourcePatternResolver
                                                .getClassLoader().loadClass(
                                                        className));
                            }
                        }
                    }
                } catch (IOException ex) {
                    if (pkg.indexOf("test") != -1
                            || pkg.indexOf("sample") != -1) {
                        continue;
                    }
                    throw new MappingException(
                            "Failed to scan classpath for unlisted classes", ex);
                } catch (ClassNotFoundException ex) {
                    if (pkg.indexOf("test") != -1
                            || pkg.indexOf("sample") != -1) {
                        continue;
                    }
                    throw new MappingException(
                            "Failed to load annotated classes from classpath",
                            ex);
                }
            }
        }
    }

    /**
     * Specify annotated classes, for which mappings will be read from
     * class-level JDK 1.5+ annotation metadata.
     * 
     * @see org.hibernate.cfg.AnnotationConfiguration#addAnnotatedClass(Class)
     */
    public void setAnnotatedClasses(Class<?>[] annotatedClasses) {
        this.annotatedClasses =annotatedClasses;
    }

    /**
     * Specify the names of annotated packages, for which package-level JDK 1.5+
     * annotation metadata will be read.
     * 
     * @see org.hibernate.cfg.AnnotationConfiguration#addPackage(String)
     */
    public void setAnnotatedPackages(String[] annotatedPackages) {
        this.annotatedPackages =annotatedPackages;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setConfigurationClass(Class configurationClass) {
        if (configurationClass == null
                || !AnnotationConfiguration.class
                        .isAssignableFrom(configurationClass)) {
            throw new IllegalArgumentException(
                    "AnnotationSessionFactoryBean only supports AnnotationConfiguration or subclasses");
        }
        super.setConfigurationClass(configurationClass);
    }

    /**
     * Specify custom type filters for Spring-based scanning for entity classes.
     * <p>
     * Default is to search all specified packages for classes annotated with
     * <code>@javax.persistence.Entity</code>,
     * <code>@javax.persistence.Embeddable</code> or
     * <code>@javax.persistence.MappedSuperclass</code>.
     * 
     * @see #setPackagesToScan
     */
    public void setEntityTypeFilters(TypeFilter[] entityTypeFilters) {
        this.entityTypeFilters = entityTypeFilters;
    }

    /**
     * Set whether to use Spring-based scanning for entity classes in the
     * classpath instead of listing annotated classes explicitly.
     * <p>
     * Default is none. Specify packages to search for autodetection of your
     * entity classes in the classpath. This is analogous to Spring's
     * component-scan feature (
     * {@link org.springframework.context.annotation.ClassPathBeanDefinitionScanner}
     * ).
     */
    public void setPackagesToScan(String[] packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        resourcePatternResolver = new PathMatchingResourcePatternResolver();
    }

}
