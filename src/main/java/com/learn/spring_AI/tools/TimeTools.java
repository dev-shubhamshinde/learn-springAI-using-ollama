package com.learn.spring_AI.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class TimeTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTools.class);

    @Tool(name="getCurrentLocalTime", description = "Get the current time in the user's timezone")
    public String getCurrentLocalTime() {
        LOGGER.info("Returning the current local time.");
        return LocalTime.now().toString();
    }

    @Tool(name = "getCurrentTime",
            description = "Get the current time in a specified location. Input must be a valid IANA timezone ID (e.g., 'Asia/Kolkata' for India, 'America/New_York' for New York).")
    public String getCurrentTime(@ToolParam(
            description = "A valid IANA timezone ID string, e.g. 'Asia/Kolkata', 'Europe/London', 'America/Chicago'") String timeZone) {
        LOGGER.info("Returning the current time in the timezone {}", timeZone);
        return "Current time in " + timeZone + " is: " + LocalTime.now(ZoneId.of(timeZone)).toString();
    }
}