const test = require("node:test");
const assert = require("node:assert/strict");

const contactReducer = require("./contactReducer").default;

test("contactReducer: SEND_MESSAGE_PENDING sets sending=true and clears error", () => {
  const prevState = { sending: false, sendFulfilled: true, error: "boom" };

  const nextState = contactReducer(prevState, { type: "SEND_MESSAGE_PENDING" });

  // Reducers should return a new object (immutability)
  assert.notStrictEqual(nextState, prevState);
  assert.deepEqual(nextState, {
    sending: true,
    sendFulfilled: false,
    error: null,
  });
});
