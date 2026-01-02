import { test, expect } from '@playwright/test';

test('UAT-01: Homepage loads successfully', async ({ page }) => {
  await page.goto('/');
  await expect(page).toHaveTitle(/E4L/i);
});
