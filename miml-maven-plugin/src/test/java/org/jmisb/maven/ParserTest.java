package org.jmisb.maven;

import static org.testng.Assert.*;

import java.io.File;
import org.testng.annotations.Test;

/** Unit tests for Parser. */
public class ParserTest {

    public ParserTest() {}

    @Test
    public void checkEnumerationFile() {
        File enumFile = new File("src/test/miml/enumeration.miml");
        assertTrue(enumFile.exists());
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        Parser uut = new Parser(parserConfiguration);
        Models models = uut.processMimlFile(enumFile);
        assertEquals(models.getEnumerationModels().size(), 4);
        assertEquals(models.getClassModels().size(), 0);
    }

    @Test
    public void checkClassFile() {
        File classFile = new File("src/test/miml/class.miml");
        assertTrue(classFile.exists());
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        Parser uut = new Parser(parserConfiguration);
        Models models = uut.processMimlFile(classFile);
        assertEquals(models.getEnumerationModels().size(), 0);
        assertEquals(models.getClassModels().size(), 6);
    }

    @Test
    public void checkFiles() {
        File directory = new File("src/test/miml/");
        ParserConfiguration parserConfiguration = makeConfig();
        Parser uut = new Parser(parserConfiguration);
        Models models = uut.processFiles(directory);
        assertEquals(models.getEnumerationModels().size(), 4);
        assertEquals(models.getClassModels().size(), 6);
        ClassModel mimd = models.findClassByName("MIMD");
        assertTrue(mimd.isTopLevel());
        ClassModel base = models.findClassByName("Base");
        assertTrue(base.isIsAbstract());
    }

    private ParserConfiguration makeConfig() {
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setTopLevelClassName("MIMD");
        parserConfiguration.setPackageNameBase("test.mimlparser");
        assertEquals(parserConfiguration.getPackageNameBase(), "test.mimlparser");
        return parserConfiguration;
    }

    @Test
    public void checkNoSuchFile() {
        File nosuchfile = new File("src/not_there.miml");
        assertFalse(nosuchfile.exists());
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        Parser uut = new Parser(parserConfiguration);
        Models models = uut.processMimlFile(nosuchfile);
        assertNotNull(models);
        assertEquals(models.getClassModels().size(), 0);
        assertEquals(models.getEnumerationModels().size(), 0);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void checkBadBlockLine() {
        MimlTextBlock block = new MimlTextBlock();
        block.addLine("blahblah{}");
        Parser uut = new Parser(makeConfig());
        uut.processBlock(block);
    }

    @Test
    public void checkEmptyBlock() {
        MimlTextBlock block = new MimlTextBlock();
        Parser uut = new Parser(makeConfig());
        AbstractModel model = uut.processBlock(block);
        assertNull(model);
    }

    @Test
    public void checkEmptyEnumeraitionBlock() {
        MimlTextBlock block = new MimlTextBlock();
        block.addLine("enumeration {");
        block.addLine("");
        block.addLine("}");
        Parser uut = new Parser(makeConfig());
        AbstractModel model = uut.processBlock(block);
        assertTrue(model instanceof EnumerationModel);
    }
}
