package com.train.utils

import redis.clients.jedis.{Jedis, JedisPool}

//获取redis连接
object MakeRedisConn {

  //创建redis连接的方法
  def makeRedis(index : Int = 0) ={
    //创建redis连接池,如果连接的是本地的redis数据库,ip和端口可以省略
    val pool: JedisPool = new JedisPool()
    //从线程池中拿出线程
    val jedis: Jedis = pool.getResource()
    jedis
  }
}
