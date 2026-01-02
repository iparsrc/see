import { defineConfig } from "@playwright/test";
import os from "node:os";
import path from "node:path";

const isCI = !!process.env.CI;

export default defineConfig({
  testDir: "./e2e",
  timeout: 30_000,
  retries: 0,
  // In CI we don't want artifacts written into the repo workspace.
  outputDir: isCI
    ? path.join(os.tmpdir(), "playwright-test-results")
    : "test-results",
  use: {
    baseURL: process.env.BASE_URL || "http://192.168.56.9:3002",
    headless: true,
    screenshot: isCI ? "off" : "only-on-failure",
    video: isCI ? "off" : "retain-on-failure",
  },
  // Terminal output in CI, HTML report locally.
  reporter: isCI
    ? "line"
    : [["html", { outputFolder: "playwright-report", open: "never" }]],
});
