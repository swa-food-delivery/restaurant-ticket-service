CREATE TABLE IF NOT EXISTS TICKETS
(
    TICKET_ID uuid NOT NULL PRIMARY KEY,
    RESTAURANT_ID uuid NOT NULL,
    ORDER_ID uuid NOT NULL,
    STATUS VARCHAR(32)  NOT NULL
);