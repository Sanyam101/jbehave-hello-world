import com.thoughtworks.paranamer.NullParanamer;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.PassingUponPendingStep;
import org.jbehave.core.failures.RethrowingFailure;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.AbsolutePathCalculator;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.reporters.PrintStreamStepdocReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.*;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class Stories extends JUnitStories {

    private Configuration configuration;

    public Stories() {
        super();
        configuration = new Configuration() {};
        configuration.useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats().withFormats(Format.CONSOLE, Format.TXT, Format.HTML));
        configuration.useFailureStrategy(new RethrowingFailure());
        configuration.useKeywords(new LocalizedKeywords(Locale.ENGLISH));
        configuration.usePathCalculator(new AbsolutePathCalculator());
        configuration.useParameterControls(new ParameterControls());
        configuration.useParameterConverters(new ParameterConverters());
        configuration.useParanamer(new NullParanamer());
        configuration.usePendingStepStrategy(new PassingUponPendingStep());
        configuration.useStepCollector(new MarkUnmatchedStepsAsPending());
        configuration.useStepdocReporter(new PrintStreamStepdocReporter());
        configuration.useStepFinder(new StepFinder());
        configuration.useStepMonitor(new SilentStepMonitor());
        configuration
                .useStepPatternParser(new RegexPrefixCapturingPatternParser());
        configuration.useStoryControls(new StoryControls());
        configuration.useStoryParser(new RegexStoryParser(configuration
                .keywords()));
        configuration.useStoryPathResolver(new UnderscoredCamelCaseResolver());
        configuration.useViewGenerator(new FreemarkerViewGenerator());
        configuration.useStoryLoader(new LoadFromClasspath());

        EmbedderControls embedderControls = configuredEmbedder()
                .embedderControls();
        embedderControls.doBatch(false);
        embedderControls.doGenerateViewAfterStories(true);
        embedderControls.doIgnoreFailureInStories(false);
        embedderControls.doIgnoreFailureInView(false);
        embedderControls.doSkip(false);
        embedderControls.doVerboseFailures(false);
        embedderControls.doVerboseFiltering(false);
        embedderControls.useStoryTimeoutInSecs(300);
        embedderControls.useThreads(1);
    }

    @Override
    public Configuration configuration() {
        return configuration;
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        // varargs, can have more that one steps classes
        return new InstanceStepsFactory(configuration(), new Steps());
    }

    @Override
    protected List<String> storyPaths() {
        return Arrays.asList("stories/a_story.story");
    }
}