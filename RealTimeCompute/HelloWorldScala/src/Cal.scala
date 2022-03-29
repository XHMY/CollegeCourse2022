import scala.io.StdIn.{readChar, readLine, readLong}

object Cal {
  def main(args: Array[String]): Unit = {
    print("please enter first number: ")
    val num1 = readLong()
    print("please enter second number: ")
    val num2 = readLong()
    print("please enter operator: ")
    val op = readChar()
    val result = op match {
      case '+' => num1 + num2
      case '-' => num1 - num2
      case '*' => num1 * num2
      case '/' => num1 / num2
      case _ => "invalid operator"
    }
    println(result)
    print("enter 'q' to quit, or any other key to continue: ")
    val input = readLine()
    if (input == "q")
      println("bye")
    else
      main(args)
  }
}
