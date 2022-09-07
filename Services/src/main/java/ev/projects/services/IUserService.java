package ev.projects.services;

import ev.projects.models.User;

public interface IUserService {

    boolean usernameExists(String username);
    void add(User user);
    void update(User user);
    void removeById(long ID);

}
