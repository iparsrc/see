## Group 2

**Memebers:**

- Darinela Andronovici
- Katarina Božič
- Parsa Akbari Chianeh

**Deliverables:**

- A mono-repository including backend, frontend, and infrastructure codes.
- A vagrant virtual machine specification and ansible playbook to provision it.
- Docker containers running the Dev, Staging, and Prod environments inside the VM.
- Test scripts (unit tests, integration tests, and end-to-end/user-acceptance tests).
- GitLab as VCS, and GitLab Runner (shell and docker providers) for running the CI/CD pipelines.

## Pre-requisites

1. OS Ubuntu (18.04 or higher)
2. VirtualBox (v 6.0 or higher)
3. Vagrant (v 2.2.5 or higher)
4. Ansible (v 2.7.5 or higher)

In order to install the dependencies, run the following command:

```sh
sudo apt update && sudo apt install -y \
  virtualbox \
  vagrant \
  ansible
```

If VirtualBox kernel modules fail to build, also run:

```sh
sudo apt install -y dkms build-essential linux-headers-$(uname -r)
```

## Getting started

Clone the repository:

```sh
git clone https://github.com/iparsrc/see.git
cd see
```

Disable KVM (Linux only):

```sh
sudo modprobe -r kvm_intel
sudo modprobe -r kvm
```

Run the virtual machine:

```sh
cd infrastructure
vagrant up
```

Open the following URL in browser (GitLab):

```sh
http://192.168.56.9
```

Login with the following credentials:

```sh
username: student
password: xxcc0xxcc
```

Visit the project/repository using the following URL:

```sh
http://192.168.56.9/student/see
```

Check the project pipelines, a job must exist for each environment with the specified stages below:

```sh
dev:
  - dev-backend-test
  - dev-backend-build
  - dev-frontend-test
  - dev-frontend-build
  - dev-deploy

staging:
  - staging-deploy
  - staging-integration-tests
  - staging-acceptance-tests

prod:
  - prod-deploy
```

When all the jobs are finished successfully you can access each environment using the following URLs:

```sh
http://192.168.56.9:8001 <dev-backend>
http://192.168.56.9:8002 <dev-frontend>
http://192.168.56.9:3001 <staging-backend>
http://192.168.56.9:3002 <staging-frontend>
http://192.168.56.9:9001 <prod-backend>
http://192.168.56.9:9002 <prod-frontend>
```
