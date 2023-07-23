package com.jarikkomarik.readitlater;

import com.jarikkomarik.readitlater.service.UtilService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilServiceTest {
    private UtilService utilService = new UtilService();

    @Test
    public void testValidUrl() {
        String validUrl = "https://www.example.com";
        assertTrue(utilService.urlIsValid(validUrl));
    }

    @Test
    public void testInvalidUrl() {
        String invalidUrl = "invalid-url";
        assertFalse(utilService.urlIsValid(invalidUrl));
    }

    @Test
    public void testMixedTextWithValidUrl() {
        String mixedTextWithValidUrl = "Follow this URL: www.example.com";
        assertTrue(utilService.urlIsValid(mixedTextWithValidUrl));
    }

    @Test
    public void testMixedTextWithInvalidUrl() {
        String mixedTextWithInvalidUrl = "Follow this URL: invalid-url";
        assertFalse(utilService.urlIsValid(mixedTextWithInvalidUrl));
    }

    @Test
    public void testGetUrlWithValidUrl() {
        String validUrl = "https://www.example.com";
        assertEquals(validUrl, utilService.getUrl(validUrl));
    }

    @Test
    public void testGetUrlWithInvalidUrl() {
        String invalidUrl = "invalid-url";
        assertNull(utilService.getUrl(invalidUrl));
    }

    @Test
    public void testGetUrlWithMixedTextAndValidUrl() {
        String mixedTextWithValidUrl = "Follow this URL: www.example.com";
        assertEquals("www.example.com", utilService.getUrl(mixedTextWithValidUrl));
    }

    @Test
    public void testGetUrlWithMixedTextAndInvalidUrl() {
        String mixedTextWithInvalidUrl = "Follow this URL: invalid-url";
        assertNull(utilService.getUrl(mixedTextWithInvalidUrl));
    }

    @Test
    public void testGetUrlWithLongerPath() {
        String mixedTextWithInvalidUrl = "https://www.simform.com/blog/angular-vs-react/";
        assertEquals(mixedTextWithInvalidUrl, utilService.getUrl(mixedTextWithInvalidUrl));
    }

}