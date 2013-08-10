import junit.framework.Assert;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class Steps {

    private String word = "";

    public Steps(){
    }
    
    @Given("A String with $aWord exists")
    public void setupAInitialWord(String aWord) {
        word = aWord;
    }

    @When("I concatenate $aWord")
    public void appendAWord(String aWord) {
        word += " " + aWord;
    }

    @Then("The result of the concatenation is $aWord")
    public void cartContentsIsEmpty(String aWord) {
        Assert.assertEquals(aWord, word);
    }
}
