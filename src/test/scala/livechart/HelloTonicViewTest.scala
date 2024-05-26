package livechart

import com.raquo.laminar.api.L.*
import com.raquo.laminar.modifiers.RenderableText
import livechart.utils.UnitSpec
import tonic.HelloWorldView
//import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.*
import org.scalatest.matchers.should.Matchers.*

class HelloTonicViewTest extends UnitSpec {


  it("should have size 0") {
    Set.empty.size shouldBe 0
  }

  it("AK test") {
    val elem = HelloWorldView.renderExample()

    mount(elem)
    //      elem.ref.children
    expectNode(
      div of (
        "Hello",
        sentinel
      )
    )
  }

  it("Chicken child.text with stream") {

    val bus = new EventBus[String]

    val el: HtmlElement = div(
      "Hello",
      child.text <-- bus
    )

    mount(el)

    expectNode(
      div of (
        "Hello",
        sentinel
      )
    )

    // --

    bus.emit("a")

    expectNode(
      div of (
        "Hello",
        "a"
      )
    )

    // --

    bus.emit("b")

    expectNode(
      div of (
        "Hello",
        "b"
      )
    )
  }

}
