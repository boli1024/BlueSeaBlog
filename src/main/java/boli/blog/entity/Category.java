package boli.blog.entity;

public class Category {

    private int id;
    private String name;
    private String introduction;
    private int blogNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getBlogNumber() {
        return blogNumber;
    }

    public void setBlogNumber(int blogNumber) {
        this.blogNumber = blogNumber;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
