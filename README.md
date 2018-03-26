sevendegree_learning

这是一个由SSM框架开发的后端系统，主要提供各种访问接口。
主要有用户，商品，订单，购物车等模块，每个模块都提供了一些必要的接口。大部分都是post接口，也有一些提供查询的get接口。

版本上，1.X版本是单机案例，2.X版本加入了Tomcat集群和Redis分布式，登录从基于session转变为基于cookies的redis存储登录信息的单点登录模式。
