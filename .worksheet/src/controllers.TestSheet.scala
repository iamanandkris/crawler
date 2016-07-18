package controllers
import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.mockito.cglib.beans.BeanMap
import scala.beans.BeanInfo
object TestSheet {


	case class aa(val paramA:String, val paramB:String){
		val k:String="a"
		var l:String=_
		def m:Unit={println("hi");}
	 
	}
	
	
	
}
