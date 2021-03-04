package com.jsimple.community.dto;

import lombok.Data;

@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;   //优先级

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}

