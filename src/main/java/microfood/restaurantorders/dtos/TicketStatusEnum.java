package microfood.restaurantorders.dtos;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum TicketStatusEnum {
    PENDING {
        @Override
        public Set<TicketStatusEnum> getNextStates() {
            return new HashSet<>(Arrays.asList(ACCEPTED, DECLINED));
        }

        @Override
        public Set<TicketStatusEnum> getPreviousStates() {
            return new HashSet<>();
        }

    },
    ACCEPTED {
        @Override
        public Set<TicketStatusEnum> getNextStates() {
            return new HashSet<>(Collections.singletonList(PREPARED));
        }

        @Override
        public Set<TicketStatusEnum> getPreviousStates() {
            return new HashSet<>(Collections.singletonList(PENDING));
        }
    },
    DECLINED {
        @Override
        public Set<TicketStatusEnum> getNextStates() {
            return new HashSet<>();
        }

        @Override
        public Set<TicketStatusEnum> getPreviousStates() {
            return new HashSet<>(Collections.singletonList(PENDING));
        }
    },
    PREPARED {
        @Override
        public Set<TicketStatusEnum> getNextStates() {
            return new HashSet<>();
        }

        @Override
        public Set<TicketStatusEnum> getPreviousStates() {
            return new HashSet<>(Collections.singletonList(ACCEPTED));
        }
    };

    public abstract Set<TicketStatusEnum> getNextStates();

    public abstract Set<TicketStatusEnum> getPreviousStates();


}
