package com.capgemini.cobigen.javaplugin.integrationtest;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Assert;
import org.junit.Test;

import com.capgemini.cobigen.CobiGen;
import com.capgemini.cobigen.config.ContextConfiguration.ContextSetting;
import com.capgemini.cobigen.extension.to.TemplateTo;
import com.capgemini.cobigen.javaplugin.integrationtest.common.AbstractIntegrationTest;
import com.capgemini.cobigen.javaplugin.util.JavaParserUtil;

/**
 * Test suite for testing the provided template methods correctly integrated with cobigen-core
 * @author mbrunnli (25.10.2014)
 */
public class TemplateMethodsTest extends AbstractIntegrationTest {

    /**
     * Tests the isAbstract template method integration
     * @throws Exception
     *             test fails
     * @author mbrunnli (25.10.2014)
     */
    @Test
    public void testIsAbstractMethod() throws Exception {

        CobiGen cobiGen = new CobiGen(cobigenConfigFolder);
        File tmpFolderCobiGen = tmpFolder.newFolder("cobigen_output");
        cobiGen
            .setContextSetting(ContextSetting.GenerationTargetRootPath, tmpFolderCobiGen.getAbsolutePath());

        List<TemplateTo> templates = cobiGen.getMatchingTemplates(this.getClass());

        boolean methodTemplateFound = false;
        for (TemplateTo template : templates) {
            if (template.getId().equals("isAbstractTemplate")) {
                cobiGen.generate(getClass(), template, false);
                File expectedFile =
                    new File(tmpFolderCobiGen.getAbsoluteFile() + SystemUtils.FILE_SEPARATOR
                        + "isAbstractOutput.txt");
                Assert.assertTrue(expectedFile.exists());
                Assert.assertEquals("falsetruetrue", FileUtils.readFileToString(expectedFile));
                methodTemplateFound = true;
                break;
            }
        }

        if (!methodTemplateFound) {
            throw new AssertionFailedError("Test template not found");
        }
    }

    /**
     * Tests the isSubtypeOf template method integration
     * @throws Exception
     *             test fails
     * @author mbrunnli (25.10.2014)
     */
    @Test
    public void testIsSubtypeOfMethod() throws Exception {

        CobiGen cobiGen = new CobiGen(cobigenConfigFolder);
        File tmpFolderCobiGen = tmpFolder.newFolder("cobigen_output");
        cobiGen
            .setContextSetting(ContextSetting.GenerationTargetRootPath, tmpFolderCobiGen.getAbsolutePath());

        List<TemplateTo> templates = cobiGen.getMatchingTemplates(this.getClass());

        boolean methodTemplateFound = false;
        for (TemplateTo template : templates) {
            if (template.getId().equals("isSubtypeOfTemplate")) {
                cobiGen.generate(getClass(), template, false);
                File expectedFile =
                    new File(tmpFolderCobiGen.getAbsoluteFile() + SystemUtils.FILE_SEPARATOR
                        + "isSubtypeOfOutput.txt");
                Assert.assertTrue(expectedFile.exists());
                Assert.assertEquals("truetruefalsefalsefalse", FileUtils.readFileToString(expectedFile));
                methodTemplateFound = true;
                break;
            }
        }

        if (!methodTemplateFound) {
            throw new AssertionFailedError("Test template not found");
        }
    }

    /**
     * Tests whether the methods could be also retrieved for array inputs
     * @throws Exception
     *             test fails
     * @author mbrunnli (29.10.2014)
     */
    @Test
    public void testCorrectClassLoaderForMethods() throws Exception {

        CobiGen cobiGen = new CobiGen(cobigenConfigFolder);
        File tmpFolderCobiGen = tmpFolder.newFolder("cobigen_output");
        cobiGen
            .setContextSetting(ContextSetting.GenerationTargetRootPath, tmpFolderCobiGen.getAbsolutePath());

        Object[] inputArr = new Object[2];
        File thisClassFile =
            new File("src/test/java/" + getClass().getPackage().getName().replaceAll("\\.", "/") + "/"
                + getClass().getSimpleName() + ".java");
        inputArr[0] = JavaParserUtil.getFirstJavaClass(new FileReader(thisClassFile));
        inputArr[1] = getClass();

        List<TemplateTo> templates = cobiGen.getMatchingTemplates(inputArr);

        boolean methodTemplateFound = false;
        for (TemplateTo template : templates) {
            if (template.getId().equals("emptyTemplate")) {
                cobiGen.generate(inputArr, template, false);
                File expectedFile =
                    new File(tmpFolderCobiGen.getAbsoluteFile() + SystemUtils.FILE_SEPARATOR
                        + "emptyTemplate.txt");
                Assert.assertTrue(expectedFile.exists());
                methodTemplateFound = true;
                break;
            }
        }

        if (!methodTemplateFound) {
            throw new AssertionFailedError("No template found");
        }
    }
}