import org.apache.spark.{SparkConf, SparkContext}

object WordCound {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WordCount").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rdd = sc.textFile("src/main/resources/word.txt")
    val wordCount = rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    wordCount.foreach(println)
  }

}
