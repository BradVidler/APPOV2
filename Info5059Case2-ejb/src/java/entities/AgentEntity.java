package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "AgentEntity.findByAgentno", query = "SELECT a FROM AgentEntity a WHERE a.agentno = :agentno"),
})
public class AgentEntity implements Serializable {
    @Id
    @Column(name = "agentno", nullable = false)
    private int agentno;
    @Column(name = "password", nullable = false)
    private String password;

    public int getAgentno() {
        return agentno;
    }

    public void setAgentno(int agentno) {
        this.agentno = agentno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public AgentEntity()
    {  
    }
    
    public AgentEntity(int agentno, String password) {
        this.agentno = agentno;
        this.password = password;
    }
    
    
}
