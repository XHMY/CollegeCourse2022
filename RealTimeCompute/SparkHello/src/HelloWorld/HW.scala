package HelloWorld
import org.apache.spark.{SparkConf, SparkContext}

object HW {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("HelloWorld").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(List(1, 2, 3, 4, 5))
    val result = rdd.map(x => x * x)
    result.foreach(println)
  }
}
