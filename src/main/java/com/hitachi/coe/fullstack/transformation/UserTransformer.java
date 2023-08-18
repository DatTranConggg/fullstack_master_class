package com.hitachi.coe.fullstack.transformation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hitachi.coe.fullstack.entity.User;
import com.hitachi.coe.fullstack.model.UserModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;

/**
 * The class UserTransformer is convert entity to DTO.
 *
 * @author ktrandangnguyen
 *
 */
@Component
public class UserTransformer extends AbstractCopyPropertiesTransformer<User, UserModel>
    implements EntityToModelTransformer<User, UserModel, Integer>{
  
  /**
   * Transformer array entities to array DTO.
   *
   * @author ktrandangnguyen
   * @param entities {@link List} of {@link User}
   * @return {@link List} of {@link UserModel}
   */
  public List<UserModel> applyList(List<User> entities) {
    if (null == entities || entities.isEmpty()) {
      return Collections.emptyList();
    }
    
    return entities.stream().map(this::apply)
        .collect(Collectors.toList());
  }
  @Override
  public UserModel apply(User entity) {
    UserModel model = new UserModel();
    model.setId(entity.getId());
    model.setName(entity.getName());
    model.setEmail(entity.getEmail());
    model.setGroupId(entity.getGroup().getId());
    return model;
  }
}
