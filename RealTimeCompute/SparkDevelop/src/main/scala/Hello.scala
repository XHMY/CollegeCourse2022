import org.apache.spark.{SparkConf, SparkContext}

object Hello {
  var base_local_path: String = "C:/Users/zyf6/Documents/GitHub/CollegeCourse2022/RealTimeCompute/SparkDevelop"


  def sparkWordCount(inputFile: String, outputFile: String) {
    val conf = new SparkConf().setAppName("Word Count").setMaster("local")
    val sc = new SparkContext(conf)
    val input = sc.textFile(inputFile)
    val words = input.flatMap(line => line.split(" "))
    val counts = words.map(word => (word, 1)).reduceByKey{case (x, y) => x + y}
    counts.saveAsTextFile(outputFile)
  }


  def sparkHello(name: String) = {
    val conf = new SparkConf().setAppName("Hello").setMaster("local")
    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(List("Hello", name))
    rdd.collect().foreach(println)
  }

  def main(args: Array[String]): Unit = {
    println("Hello, world!")
//   sparkHello("Yokey Xiao")
    sparkWordCount(base_local_path + "/src/main/resources/word.txt",
      base_local_path + "/src/main/resources/output")
  }

}
