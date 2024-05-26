package livechart.helpers

import com.raquo.laminar.api.L
import com.raquo.laminar.api.L._
import org.scalajs.dom
import org.scalatest.Assertion

import scala.concurrent.{ExecutionContext, Future}

trait Harness {

  def harness(page: HtmlElement)(block: => Future[Assertion])(implicit ex: ExecutionContext): Future[Assertion] = {
    import dom.document

    val el = document.createElement("div")

    document.body.appendChild(el)

    val root = render(el, page)

    block
      .map { r =>
        root.unmount()
        r
      }
      .recover {
        case e =>
          root.unmount()
          throw e
      }

  }
}