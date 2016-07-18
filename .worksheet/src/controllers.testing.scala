package controllers

object testing {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(81); 
  println("Welcome to the Scala worksheet");$skip(79); 
  
  val fullCheck = new Base with FirstLevel with SecondLevel with ThirdLevel;System.out.println("""fullCheck  : controllers.Base with controllers.FirstLevel with controllers.SecondLevel with controllers.ThirdLevel = """ + $show(fullCheck ));$skip(21); val res$0 = 
  
  fullCheck.check;System.out.println("""res0: String = """ + $show(res$0));$skip(151); 
 val integers = new Generator[Int]{
 	val rand = new java.util.Random
 	def generate = {
 		val m = rand.nextInt()
 		if (m < 0) -1*m
 		else m
 	}
 };System.out.println("""integers  : controllers.Generator[Int]{val rand: java.util.Random} = """ + $show(integers ));$skip(117); 
 def choose(lo:Int, hi:Int):Generator[Int]={
 	val t = for(x <- integers) yield lo + x % (hi-lo)
 	println(t)
 	t
 };System.out.println("""choose: (lo: Int, hi: Int)controllers.Generator[Int]""");$skip(34); 
 
 val m = choose(10,20).generate;System.out.println("""m  : Int = """ + $show(m ));$skip(124); 
 val test1 = List(List(1,1),List(2,2),List(3,3),List(4,4),List(5,5),List(6,6)).flatMap(f => {if (f(0) %2 == 0) f else Nil});System.out.println("""test1  : List[Int] = """ + $show(test1 ));$skip(130); 
 val test2 = List(List(1,1),List(2,2),List(3,3),List(4,4),List(5,5),List(6,6)).flatMap(f => {if (f(0) %2 == 0) List(f) else Nil});System.out.println("""test2  : List[List[Int]] = """ + $show(test2 ));$skip(91); 
                                                  
 val ll = List(1).flatMap(f=> Set(f*2));System.out.println("""ll  : List[Int] = """ + $show(ll ))}
 //val booleans = for (x <- integers) yield x >0
 
 //val booleans = integers map (x => x > 0)
 //booleans.generate
}
  
 abstract class Base {
 	def check()="Performing the check"
 }
 
 trait FirstLevel extends Base{
 	 override def check()={
 		s"Performing the First LevelCheck + ${super.check}"
 	}
 }
 
 trait SecondLevel extends Base{
 	 override def check()={
 		s"Performing the Second LevelCheck + ${super.check}"
 	}
 }
 
 trait ThirdLevel extends Base{
 	 override def check()={
 		s"Performing the Third LevelCheck + ${super.check}"
 	}
 }
 
 trait Generator[+T]{
 	self =>
 	def generate:T
 	
 	def map[S](f:T=>S):Generator[S] = new Generator[S]{
 		def generate = f(self.generate)
 	}
 	
 	//def flatMap[S](f:T=>Generator[S]):Generator[S] = new Generator[S]{
 	//	def generate = f(self.generate).generate
 	//}
 }
 
 
 
   
  