package crawlerinformation

import play.api.libs.json.Json

/**
  * Created by AnandKrishnan on 7/16/2016.
  */
case class SearchInfo (url:String, search_item:String)

object SearchInfo{
  implicit val reads = Json.reads[SearchInfo]
  implicit val writes = Json.writes[SearchInfo]
}
