package com.duobang.middleware.i.cache;

import java.util.List;

public interface IReportPrefersences {

    <T> List<T> getReportUsers(Class<?> ReportUserClass);

    <T> void saveReportUsers(List<T> ReportUsers);

    void clear();
}
