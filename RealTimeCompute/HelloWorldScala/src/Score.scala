import scala.io.StdIn.{readDouble, readInt}

object Score {
  def main(args: Array[String]): Unit = {
    println("How many classes are there: ")
    val class_num = readInt()
    var total_people = 0.0
    var max = 0.0
    var ma = 0.0
    var min = 100.0
    var mi = 100.0
    var sum = 0.0
    var total = 0.0

    for(i<- 1 to class_num){
      println(s"Input No.${i} class's people:")
      val people = readInt()
      total_people = total_people+people
      var score = new Array[Double](people)
      max = 0.0
      min = 100.0
      sum = 0.0
      for(j<-0 until people){
        println(s"Please input ${i} class no.${j+1} student's score: ")
        score(j)=readDouble()
        sum = sum+score(j)
        if(score(j)>max) max=score(j)
        if(score(j)<min) min=score(j)
        if(max>ma) ma=max
        if(min<mi) mi=min

      }
      total = total+sum
      println(f"Now, the best score is ${max} , worst score is ${min}, average${sum/people}%5.2f")
    }
    println(f"Grade's best score is ${ma}, worst score is ${mi} , average${total/total_people}%5.2f ")



  }
}












