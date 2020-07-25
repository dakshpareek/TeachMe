package com.teachme.teachme.DTO;

import com.teachme.teachme.entity.Skill;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SkillWrapper {
    List<Integer> skillIdList;
}
