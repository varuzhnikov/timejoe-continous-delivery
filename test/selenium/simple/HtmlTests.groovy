package simple

import grails.plugins.selenium.SeleniumAware
import org.junit.Test

@Mixin(SeleniumAware)
class HtmlTests extends GroovyTestCase {
    @Test
    public void testSimpleTest() throws Exception {
        selenium.open("/")
        selenium.type("id=username", "member1@timejoe.com")
        selenium.type("id=password", "password")
        selenium.click("css=input[type=\"submit\"]")
        selenium.waitForPageToLoad("30000")
        assertTrue("doesn't present", selenium.isTextPresent("You're signed in as member1@timejoe.com"))
        selenium.click("link=Logout")
        selenium.waitForPageToLoad("30000")
    }
}
