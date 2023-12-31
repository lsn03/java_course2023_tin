package edu.project3.log;

import edu.project3.StatusCode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class LogReport {


    public static final String USING_HTTP_USER_AGENT = "Используемые  http_user_agent";
    public static final String HTTP_USER_AGENT = "Http_user_agent";
    public static final String USING_REMOTE_ADDRESS = "Используемые удаленные адреса";
    public static final String ADDRESS = "Адрес";
    private final String logPath;
    private final LocalDateTime fromDate;
    private final LocalDateTime toDate;
    private final long totalRequests;
    private final double avgResponseSize;
    private final Map<String, Long> resourceCounts;
    private final Map<Integer, Long> responseCodeCounts;
    private final Map<Integer, String> responseCodeDescriptions;
    private final Map<String, Long> remoteAddresCounts;
    private final Map<String, Long> httpUserAgentCount;


    public static final StringBuilder MARKDOWN_REPORT = new StringBuilder();
    public static final StringBuilder ASCII_DOC_REPORT = new StringBuilder();

    public static final String NEW_LINE = System.lineSeparator();
    public static final String DOUBLE_NEW_LINE = System.lineSeparator() + System.lineSeparator();
    public static final String COMMON_INFO = "Общая информация";
    public static final String REQUESTED_RESOURCES = "Запрашиваемые ресурсы";
    public static final String RESPONSE_CODE = "Коды ответа";
    public static final String METRICS = "Метрика";
    public static final String VALUES = "Значение";
    public static final String FILE_S = "Файл(-ы)";
    public static final String DATE_START = "Начальная дата";
    public static final String DATE_END = "Конечная дата";
    public static final String REQEUSTS_COUNT = "Количество запросов";
    public static final String RESPONSE_AVARAGE_WEIGHT = "Средний размер ответа";
    public static final String RESOURCE = "Ресурс";
    public static final String COUNT = "Количество";
    public static final String CODE = "Код";
    public static final String NAME_OF_CODE = "Имя";
    public static final String BYTE = "b";

    public static final String HEADER_MD = "#### ";
    public static final String HEADER_ASCII = "== ";
    public static final String WALL = "|";
    public static final String TWO_COLLUMN_MD = "|:----:|----:|";
    public static final String THIRD_COLLUMN_MD = "|:----:|:----:|----:|";
    public static final String ELEMENT_OF_PATH = "`";
    public static final String TWO_COLLUMN_ASCII_DOC = "[cols=\"2,1\", options=\"header\"]";
    public static final String THIRD_COLLUMN_ASCII_DOC = "[cols=\"3,2,1\", options=\"header\"]";
    public static final String START_END_ASCII_BLOCK = "|===";


    public LogReport(String logPath,
                     LocalDateTime fromDate,
                     LocalDateTime toDate,
                     LogStatistics logStatistics) {
        this.logPath = logPath;
        this.fromDate = fromDate;
        this.toDate = toDate;

        this.totalRequests = logStatistics.getTotalRequests();
        this.avgResponseSize = logStatistics.getAvgResponseSize();
        this.resourceCounts = logStatistics.getResourceCounts();
        this.responseCodeCounts = logStatistics.getResponseCodeCounts();
        this.remoteAddresCounts = logStatistics.getUniqueIpCounts();
        this.httpUserAgentCount = logStatistics.getHttpUserAgentCount();

        responseCodeDescriptions = new HashMap<>();
        responseCodeDescriptions.put(StatusCode.SUCCESS_STATUS_CODE, "OK");
        responseCodeDescriptions.put(StatusCode.NOT_FOUND_STATUS_CODE, "Not Found");
        responseCodeDescriptions.put(StatusCode.NOT_MODIFIED, "Not Modified");
    }

    public Map<String, Long> getRemoteAddresCounts() {
        return remoteAddresCounts;
    }

    public Map<String, Long> getHttpUserAgentCount() {
        return httpUserAgentCount;
    }

    public String getLogPath() {
        return logPath;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public double getAvgResponseSize() {
        return avgResponseSize;
    }

    public Map<String, Long> getResourceCounts() {
        return resourceCounts;
    }

    public Map<Integer, Long> getResponseCodeCounts() {
        return responseCodeCounts;
    }

    public Map<Integer, String> getResponseCodeDescriptions() {
        return responseCodeDescriptions;
    }

    public String toMarkdown() {
        MARKDOWN_REPORT.setLength(0);

        loadMetricsMd();

        loadResourcesMd();

        loadResponseCodeMd();

        loadUniqueIpCountMd();
        loadHttpAgentCountMd();

        return MARKDOWN_REPORT.toString();
    }


    public String toAsciiDoc() {

        ASCII_DOC_REPORT.setLength(0);
        loadMetricsAscii();
        loadResourcesAscii();
        loadResponseCodeAscii();
        loadUniqueIpCountAscii();
        loadHttpAgentCountAscii();
        return ASCII_DOC_REPORT.toString();
    }

    private void loadUniqueIpCountAscii() {
        ASCII_DOC_REPORT.append(buildLine(HEADER_ASCII, USING_REMOTE_ADDRESS, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(TWO_COLLUMN_ASCII_DOC, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(START_END_ASCII_BLOCK, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(WALL, ADDRESS, WALL, COUNT, NEW_LINE));

        for (Map.Entry<String, Long> entry : remoteAddresCounts.entrySet()) {
            ASCII_DOC_REPORT.append(buildLine(
                    WALL, ELEMENT_OF_PATH,
                    entry.getKey(), ELEMENT_OF_PATH,
                    WALL, entry.getValue(), NEW_LINE)
            );
        }
        ASCII_DOC_REPORT.append(buildLine(START_END_ASCII_BLOCK, DOUBLE_NEW_LINE));

    }

    private void loadHttpAgentCountAscii() {
        ASCII_DOC_REPORT.append(buildLine(HEADER_ASCII, USING_HTTP_USER_AGENT, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(TWO_COLLUMN_ASCII_DOC, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(START_END_ASCII_BLOCK, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(WALL, HTTP_USER_AGENT, WALL, COUNT, NEW_LINE));

        for (Map.Entry<String, Long> entry : httpUserAgentCount.entrySet()) {
            ASCII_DOC_REPORT.append(buildLine(
                    WALL, ELEMENT_OF_PATH,
                    entry.getKey(), ELEMENT_OF_PATH,
                    WALL, entry.getValue(), NEW_LINE)
            );
        }
        ASCII_DOC_REPORT.append(buildLine(START_END_ASCII_BLOCK, DOUBLE_NEW_LINE));

    }

    private void loadUniqueIpCountMd() {
        MARKDOWN_REPORT.append(buildLine(HEADER_MD, USING_REMOTE_ADDRESS, DOUBLE_NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(WALL, ADDRESS, WALL, COUNT, WALL, NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(TWO_COLLUMN_MD, NEW_LINE));
        for (Map.Entry<String, Long> entry : remoteAddresCounts.entrySet()) {
            MARKDOWN_REPORT.append(buildLine(
                    WALL, ELEMENT_OF_PATH,
                    entry.getKey(), ELEMENT_OF_PATH,
                    WALL, entry.getValue(), WALL,
                    NEW_LINE)
            );
        }
        MARKDOWN_REPORT.append(NEW_LINE);
    }

    private void loadHttpAgentCountMd() {
        MARKDOWN_REPORT.append(buildLine(HEADER_MD, USING_HTTP_USER_AGENT, DOUBLE_NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(WALL, HTTP_USER_AGENT, WALL, COUNT, WALL, NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(TWO_COLLUMN_MD, NEW_LINE));
        for (Map.Entry<String, Long> entry : httpUserAgentCount.entrySet()) {
            MARKDOWN_REPORT.append(buildLine(
                    WALL, ELEMENT_OF_PATH,
                    entry.getKey(), ELEMENT_OF_PATH,
                    WALL, entry.getValue(), WALL,
                    NEW_LINE)
            );
        }
        MARKDOWN_REPORT.append(NEW_LINE);
    }

    private static String buildLine(Object... elems) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object elem : elems) {
            stringBuilder.append(elem);
        }
        return stringBuilder.toString();
    }


    private void loadMetricsMd() {
        MARKDOWN_REPORT.append(buildLine(HEADER_MD, COMMON_INFO, DOUBLE_NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(WALL, METRICS, WALL, VALUES, WALL, NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(TWO_COLLUMN_MD, NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(
                WALL, FILE_S, WALL, ELEMENT_OF_PATH, logPath, ELEMENT_OF_PATH, WALL, NEW_LINE)
        );
        MARKDOWN_REPORT.append(buildLine(WALL, DATE_START, WALL, fromDate, WALL, NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(WALL, DATE_END, WALL, toDate, WALL, NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(WALL, REQEUSTS_COUNT, WALL, totalRequests, WALL, NEW_LINE)
        );
        MARKDOWN_REPORT.append(buildLine(
                WALL, RESPONSE_AVARAGE_WEIGHT, WALL, avgResponseSize, BYTE, WALL, DOUBLE_NEW_LINE)
        );

    }

    private void loadResourcesMd() {
        MARKDOWN_REPORT.append(buildLine(HEADER_MD, REQUESTED_RESOURCES, DOUBLE_NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(WALL, RESOURCE, WALL, COUNT, WALL, NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(TWO_COLLUMN_MD, NEW_LINE));
        for (Map.Entry<String, Long> entry : resourceCounts.entrySet()) {
            MARKDOWN_REPORT.append(buildLine(
                    WALL, ELEMENT_OF_PATH,
                    entry.getKey(), ELEMENT_OF_PATH,
                    WALL, entry.getValue(), WALL,
                    NEW_LINE)
            );
        }
        MARKDOWN_REPORT.append(NEW_LINE);
    }

    private void loadResponseCodeMd() {
        MARKDOWN_REPORT.append(buildLine(HEADER_MD, RESPONSE_CODE, DOUBLE_NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(WALL, CODE, WALL, NAME_OF_CODE, WALL, COUNT, WALL, NEW_LINE));
        MARKDOWN_REPORT.append(buildLine(THIRD_COLLUMN_MD, NEW_LINE));
        for (Map.Entry<Integer, Long> entry : responseCodeCounts.entrySet()) {
            var desc = responseCodeDescriptions.getOrDefault(entry.getKey(), "-");
            MARKDOWN_REPORT.append(buildLine(WALL, entry.getKey(), WALL, desc, WALL, entry.getValue(), WALL, NEW_LINE));
        }
        MARKDOWN_REPORT.append(NEW_LINE);
    }


    private void loadResponseCodeAscii() {
        ASCII_DOC_REPORT.append(buildLine(HEADER_ASCII, RESPONSE_CODE, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(THIRD_COLLUMN_ASCII_DOC, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(START_END_ASCII_BLOCK, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(WALL, CODE, WALL, NAME_OF_CODE, WALL, COUNT, NEW_LINE));

        for (Map.Entry<Integer, Long> entry : responseCodeCounts.entrySet()) {
            var desc = responseCodeDescriptions.getOrDefault(entry.getKey(), "-");
            ASCII_DOC_REPORT.append(buildLine(WALL, entry.getKey(), WALL, desc, WALL, entry.getValue(), NEW_LINE));
        }
        ASCII_DOC_REPORT.append(buildLine(START_END_ASCII_BLOCK, DOUBLE_NEW_LINE));
    }

    private void loadResourcesAscii() {
        ASCII_DOC_REPORT.append(buildLine(HEADER_ASCII, REQUESTED_RESOURCES, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(TWO_COLLUMN_ASCII_DOC, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(START_END_ASCII_BLOCK, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(WALL, RESOURCE, WALL, COUNT, NEW_LINE));

        for (Map.Entry<String, Long> entry : resourceCounts.entrySet()) {
            ASCII_DOC_REPORT.append(buildLine(
                    WALL, ELEMENT_OF_PATH,
                    entry.getKey(), ELEMENT_OF_PATH,
                    WALL, entry.getValue(), NEW_LINE)
            );
        }
        ASCII_DOC_REPORT.append(buildLine(START_END_ASCII_BLOCK, DOUBLE_NEW_LINE));

    }

    private void loadMetricsAscii() {
        ASCII_DOC_REPORT.append(buildLine(HEADER_ASCII, COMMON_INFO, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(TWO_COLLUMN_ASCII_DOC, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(START_END_ASCII_BLOCK, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(WALL, METRICS, WALL, VALUES, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(WALL, FILE_S, WALL, logPath, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(WALL, DATE_START, WALL, fromDate, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(WALL, DATE_END, WALL, toDate, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(WALL, REQEUSTS_COUNT, WALL, totalRequests, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(WALL, RESPONSE_AVARAGE_WEIGHT, WALL, avgResponseSize, BYTE, NEW_LINE));
        ASCII_DOC_REPORT.append(buildLine(START_END_ASCII_BLOCK, DOUBLE_NEW_LINE));
    }
}
