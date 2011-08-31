本project存放：
容器核心.
与业务无关，与网管无法，完全独立的一个core。

core包括: 
模块管理: 核心的核心，平台的基础。包含模块定义，生命周期管理，接口的暴露与导入。
spring  ：本平台基于spring，此处通过桥接使用spring。
...

--------------------

resource/下为client,server,sbi运行的配置文件，包括模块配置文件yuep.xml与core所需要的各类xml,properties配置文件。
ant脚本deploy时，会自动将其拷贝到yuep-build/的运行目录下。

-------------------------

如何运行：
运行CoreContainer.launch，如果是client或sbi,但需要修改program arguments，修改为yuep-build/client或yuep-build/sbi所在绝对路径

另外，为了能够debug其他模块的jar，需要在debug configurations里面的source里面把其他jar对应的java project加入进去，否则无法进行源代码级的调试。