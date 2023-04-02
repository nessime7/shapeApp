mamy endpoint do dodawania figur geometrycznych
@POST /api/v1/shapes
body: { type: CIRCLE, parameters: {"radius": 10.0})
body: { type: RECTANGLE, parameters: {"width": 10.0, "height": 20.0})

w response:
201 created: { id, type, radius, links: {calculate-perimeter, calculate-area}}
201 created: { id, type, width, height, links: {calculate-perimeter, calculate-area}}

ale tez response moze byc:
400 bad request: { message: "UNKNOWN_TYPE" | "INVALID_PARAMETERS" }
jesli np: poda sie jakis nieznany typ, lub zle parametry dla typu danej figury

jesli chodzi o linki w odpowiedzi to one maja uruchamiac jakis dodatkowy endpoint do
wyliczenia obwodu albo pola danej figury
propozycje kontraktu daj sama, ale body musi byc takie:

200OK
body: { shapeId: ..., area: ... links: {shape-details}}
body: { shapeId: ..., perimeter: ..., links: {shape-details}}

czy moga byc inne body? jakies wyjatki?

@GET /api/v1/shapes?type=*
- jesli type nie podano - to zwracamy wszystkie figury
- jesli type podano to zwracamy figury tylko danego typu
- apply pagination
  zwraca page czegos co extends ShapeDto

calosc pokryc testami jednostkowymi i integracyjnymi.
