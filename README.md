# weibospider4
新浪微博，腾讯微博采集器，采集公共微博然后储存在数据库中，支持的参数设置：多任务多线程，正则表达式关键字过滤微薄，由hibernate自动建立数据库和表结构
运行之前需要先启动mysql（需要将默认字符设置为utf8），然后修改jdbc.prop文件。更多配置在root-context.xml的spring 4的配置文件中，包括你的accesstoken.
运行主类：org.cheetyan.weibospider.main.Config
