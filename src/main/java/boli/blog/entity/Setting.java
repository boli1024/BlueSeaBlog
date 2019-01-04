package boli.blog.entity;

public class Setting {

    private int id;
    private int userId;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", userId=" + userId +
                ", image='" + image + '\'' +
                '}';
    }
}
