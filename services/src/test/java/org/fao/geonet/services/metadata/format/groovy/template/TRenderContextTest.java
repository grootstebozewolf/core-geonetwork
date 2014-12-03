package org.fao.geonet.services.metadata.format.groovy.template;

import org.fao.geonet.services.metadata.format.groovy.util.NavBarItem;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TRenderContextTest {

    @Test
    public void testGetModelValue() throws Exception {
        final TestObj item = new TestObj();
        final Map<String, Object> model = Collections.<String, Object>singletonMap("item", item);
        final TRenderContext context = new TRenderContext(new ByteArrayOutputStream(), model);
        assertEquals("KEY", context.getModelValue("item.inner.name"));
        assertEquals("REL", context.getModelValue("item.inner.rel"));
        assertEquals(item.toString(), context.getModelValue("item").toString());
        assertEquals(item.getInner().toString(), context.getModelValue("item.inner").toString());
    }

    @Test
    public void testEmptyPathSection() throws Exception {
        String itemKey = "item";
        final String expr = itemKey + "." + ".name";
        try {
            final TestObj item = new TestObj();
            final Map<String, Object> model = Collections.<String, Object>singletonMap(itemKey, item);
            final TRenderContext context = new TRenderContext(new ByteArrayOutputStream(), model);
            context.getModelValue(expr);
            fail("Expected an exception");
        } catch (TemplateException e) {
            assertTrue(e.getMessage(), e.getMessage().contains(expr));
            assertTrue(e.getMessage(), e.getMessage().contains("empty section"));
        }
    }

    @Test
    public void testNoProperty() throws Exception {
        String itemKey = "item";
        final String expr = itemKey + "." + "name";
        try {
            final TestObj item = new TestObj();
            final Map<String, Object> model = Collections.<String, Object>singletonMap(itemKey, item);
            final TRenderContext context = new TRenderContext(new ByteArrayOutputStream(), model);
            context.getModelValue(expr);
            fail("Expected an exception");
        } catch (TemplateException e) {
            assertTrue(e.getMessage(), e.getMessage().contains(expr));
            assertTrue(e.getMessage(), e.getMessage().contains("does not exist"));
        }
    }

    @Test
    public void testNoItem() throws Exception {
        String itemKey = "item";
        final String expr = "wrongKey.inner.name";
        try {
            final TestObj item = new TestObj();
            final Map<String, Object> model = Collections.<String, Object>singletonMap(itemKey, item);
            final TRenderContext context = new TRenderContext(new ByteArrayOutputStream(), model);
            context.getModelValue(expr);
            fail("Expected an exception");
        } catch (TemplateException e) {
            assertTrue(e.getMessage(), e.getMessage().contains(expr));
            assertTrue(e.getMessage(), e.getMessage().contains("There is no object in the model"));
        }
    }
    @Test
    public void testNullItem() throws Exception {
        String itemKey = "item";
        final String expr = itemKey + ".other.name";
        try {
            final TestObj item = new TestObj();
            final Map<String, Object> model = Collections.<String, Object>singletonMap(itemKey, item);
            final TRenderContext context = new TRenderContext(new ByteArrayOutputStream(), model);
            context.getModelValue(expr);
            fail("Expected an exception");
        } catch (TemplateException e) {
            assertTrue(e.getMessage(), e.getMessage().contains(expr));
            assertTrue(e.getMessage(), e.getMessage().contains("value of the property"));
            assertTrue(e.getMessage(), e.getMessage().contains("is null"));
        }
    }

    private static class TestObj {
        final NavBarItem inner = new NavBarItem();
        final NavBarItem other = null;

        private TestObj() {
            inner.setName("KEY");
            inner.setRel("REL");
        }

        public NavBarItem getInner() {
            return inner;
        }

        @Override
        public String toString() {
            return "TestObj";
        }
    }
}