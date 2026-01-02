# Scenario 1

## Goal

The mono-repository `see` is available in GitLab, and can be cloned with all three components (backend, frontend, infrastructure).

## Pre-conditions

- The provided VM (Vagrant + Ansible) is provisioned and running.
- GitLab is reachable at `http://192.168.56.9`.
- A non-root GitLab user exists (e.g., `student`).
- The `see` project exists in GitLab (`student/see`).

## Main Success Scenario

1. Clone the repository locally with (`username: student`, `password: xxcc0xxcc`):

```sh
git clone http://192.168.56.9/student/see.git
cd see
```

5. Verify the mono-repository structure contains all components:
   - `backend/`
   - `frontend/`
   - `infrastructure/`

## Notes

- This project is a **mono-repository**: one Git repository contains backend, frontend, and infrastructure code.
- CI/CD pipelines are designed to run only on `dev`, `staging`, and `prod` branches.

# Scenario 2

## Goal

The `dev` pipeline is successfully executed for the mono-repository when a valid commit is made to the `dev` branch.

## Pre-conditions

- The provided VM (Vagrant + Ansible) is provisioned and running.
- GitLab is reachable at `http://192.168.56.9`.
- A non-root GitLab user exists (e.g., `student`).
- The `see` project exists in GitLab (`student/see`).
- A `dev` branch exists (or will be created)

## Main Success Scenario

1. Create (or switch to) the `dev` branch:

```sh
git checkout -B dev
```

2. Make a small change in the `README.md`, then commit and push (`username: student`, `password: xxcc0xxcc`):

```sh
git add -A
git commit -m "Change to trigger dev pipeline"
git push -u origin dev
```

3. In GitLab, open the project’s pipelines page and wait for the `dev` pipeline to finish.

4. Confirm the following jobs complete successfully:

   - `dev-backend-test`
   - `dev-backend-build`
   - `dev-frontend-test`
   - `dev-frontend-build`
   - `dev-deploy`

5. Verify the deployed applications are reachable by opening `http://192.168.56.9:8002` in your browser.

## Notes

- Jobs are triggered when a valid commit is pushed.

# Scenario 3

## Goal

Showcase an **invalid (breaking) commit** on the `dev` branch that causes the `dev` pipeline to **fail** (e.g., failing backend and/or frontend tests).

## Pre-conditions

- Same pre-conditions as Scenario 2
- A `dev` branch exists (or will be created)

## Main Failure Scenario

1. Create (or switch to) the `dev` branch and push it:

```sh
git checkout -B dev
git push -u origin dev
```

2. Make an **invalid (breaking) change** and push it to `dev`. One easy way is to **uncomment** the intentionally failing test case in `backend/src/test/java/lu/uni/e4l/platform/ExpressionEvaluatorUnitTest.java` (`dummy_failingTestCase`):

```sh
# Example: uncomment the dummy failing test case:
# - backend/src/test/java/lu/uni/e4l/platform/ExpressionEvaluatorUnitTest.java
# - uncomment `@Test` and the `dummy_failingTestCase` method

git add -A
git commit -m "Introduce breaking change to fail dev pipeline"
git push
```

3. In GitLab, open the project’s pipelines page and wait for the `dev` pipeline to finish.

4. Confirm the pipeline **fails** and that at least one of the following jobs is **failed**:

   - `dev-backend-test` (expected to fail if the breaking change is in backend tests)
   - `dev-frontend-test` (expected to fail if the breaking change is in frontend tests)

5. Open the failed job logs and verify the failure corresponds to your invalid change (e.g., an assertion failure).

## Notes

- Backend tests are executed in the backend test suite.
- Frontend tests are executed in the frontend test suite.

# Scenario 4

## Goal

The `staging` pipeline deploys the mono-repository and runs integration + acceptance tests successfully after a valid commit is pushed to the `staging` branch.

## Pre-conditions

- Same pre-conditions as Scenario 2
- A `staging` branch exists (or will be created)

## Main Success Scenario

1. Create (or switch to) the `staging` branch:

```sh
git checkout -B staging
```

2. Make a small valid change (e.g., update `README.md`), then commit and push:

```sh
git add -A
git commit -m "Change to trigger staging pipeline"
git push -u origin staging
```

3. In GitLab, open the project’s pipelines page and wait for the `staging` pipeline to finish.

4. Confirm the following jobs complete successfully:

   - `staging-deploy`
   - `staging-integration-tests`
   - `staging-acceptance-tests`

5. Verify the deployed applications are reachable:
   - Backend: `http://192.168.56.9:3001`
   - Frontend: `http://192.168.56.9:3002`

## Notes

- Integration tests are executed from the backend test suite (e.g., `*IntegrationTest`).
- Acceptance tests are executed from the frontend Playwright tests (e.g., `frontend/e2e/*`).

# Scenario 5

## Goal

The `prod` pipeline deploys the mono-repository successfully after a valid commit is pushed to the `prod` branch.

## Pre-conditions

- Same pre-conditions as Scenario 2
- A `prod` branch exists (or will be created)

## Main Success Scenario

1. Create (or switch to) the `prod` branch:

```sh
git checkout -B prod
```

2. Make a small valid change (e.g., update `README.md`), then commit and push:

```sh
git add -A
git commit -m "Change to trigger prod pipeline"
git push -u origin prod
```

3. In GitLab, open the project’s pipelines page and wait for the `prod` pipeline to finish.

4. Confirm the following job completes successfully:

   - `prod-deploy`

5. Verify the deployed applications are reachable:
   - Backend: `http://192.168.56.9:9001`
   - Frontend: `http://192.168.56.9:9002`
