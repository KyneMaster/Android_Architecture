package com.duobang.middleware.i.cache;

import java.util.List;

public interface IMemberPreferences {

    <T> List<T> getMembers(Class<?> MemberClass);

    <T> void saveMembers(List<T> members);

    void clear();
}
