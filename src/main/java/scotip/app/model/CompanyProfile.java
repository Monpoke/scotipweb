package scotip.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Pierre on 24/04/2016.
 */
@Entity
@Table(name="company_profile")
public class CompanyProfile {


        @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
        private int id;

        @Column(name="TYPE", length=15, unique=true, nullable=false)
        private String type = CompanyProfileType.USER.getUserProfileType();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + id;
            result = prime * result + ((type == null) ? 0 : type.hashCode());
            return result;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyProfile that = (CompanyProfile) o;

        if (id != that.id) return false;
        return type.equals(that.type);

    }

    @Override
        public String toString() {
            return "UserProfile [id=" + id + ",  type=" + type  + "]";
        }


    }