package at.oenb.scrum.tickets;


import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by chfuss on 04.02.16.
 */

@Getter
@Setter
@ToString
@Builder
public class TicketInfo {

    String ticketNr;
    String priority;
    String titel;
    String description;

    public TicketInfo(String ticketNr, String priority, String titel, String description) {
        this.description = description;
        this.ticketNr = ticketNr;
        this.priority = priority;
        this.titel = titel;

    }

 /*   public ObjectNode getJsonNode(){
        ObjectNode ticketNode = new ObjectNode(new JsonNodeFactory(false));
        ticketNode.put("ticketNumber", ticketNr);
        ticketNode.put("priority", priority);
        ticketNode.put("titel",titel);
        ticketNode.put("description", description);
        return ticketNode;
    }
*/
}
