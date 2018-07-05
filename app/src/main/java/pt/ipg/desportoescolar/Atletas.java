package pt.ipg.desportoescolar;

public class Atletas {
    private int id;
    private String name;
    private int age;
    private int idDesporto;

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

    public double getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIdDesporto() {
        return idDesporto;
    }

    public void setIdDesporto(int IdDesporto) {
        this.idDesporto = idDesporto;
    }
}