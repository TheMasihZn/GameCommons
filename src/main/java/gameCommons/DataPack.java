package gameCommons;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.util.Objects;

public class DataPack implements Serializable {
    private long id;
    private String name;
    private int icon;

    public DataPack(long id) {
        this(id, "", 0);
    }

    public DataPack(long id, String name) {
        this(id, name, 0);
    }

    public DataPack(long id, String name, int icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    @Contract(value = "null -> false", pure = true)
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
