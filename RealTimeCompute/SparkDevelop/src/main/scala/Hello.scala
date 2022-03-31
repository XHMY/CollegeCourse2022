import org.apache.spark.{SparkConf, SparkContext}

object Hello {

  def sparkHello(name: String) = {
    var conf = new SparkConf().setAppName("Hello").setMaster("local")
    var sc = new SparkContext(conf)
    var rdd = sc.parallelize(List("Hello", name))
    rdd.collect().foreach(println)
  }

  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    sparkHello("Yokey Xiao")
  }

}
