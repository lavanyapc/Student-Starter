Course Registration — DevOps Lab

1\. Project Structure

pom.xml — Parent POM with Java 17, JUnit 5, dependency management and module configuration.

course-registration-app/ — Child Maven module containing Java source and tests.

Jenkinsfile — Jenkins Declarative Pipeline.

k8s/ — Kubernetes Deployment and Service manifests.

2\. Maven Commands



Run from the repository root:



mvn clean test

mvn package





Recorded results: BUILD SUCCESS, 16 tests, 0 failures, 0 errors, 0 skipped. Both parent and child modules built successfully.



3\. Code Changes

Fixed course-capacity validation.

Added credit validation: only 1–6 credits are accepted.

Added JUnit tests for capacity and credit boundaries.

Rejected registrations preserve student and course state.

4\. Jenkins Pipeline



The Jenkinsfile targets a Windows agent and uses bat commands. It runs tests, publishes JUnit reports, packages the application and archives the JAR.



Reports: course-registration-app/target/surefire-reports/TEST-\*.xml

Artifact: course-registration-app/target/\*.jar



Jenkins was not executed; the pipeline was authored only.



5\. Kubernetes



Deploy using:



minikube start --driver=docker

kubectl apply -f k8s/deployment.yaml

kubectl apply -f k8s/service.yaml

kubectl get deployments

kubectl get services

kubectl get pods





Scale to three replicas:



kubectl scale deployment nginx-deployment --replicas=3

kubectl rollout status deployment/nginx-deployment





The Deployment controller replaces deleted Pods to maintain the desired replica count. Record actual output for scaling, self-healing and service access.



6\. Git



Feature branch: feature/course-registration-validation



Existing commits:



e77e20f — Fix course capacity and credit validation

2554363 — Add capacity and credit boundary tests



Complete the remaining commits, merge into main, and verify a clean working tree.



Generated target/ files are excluded through .gitignore.

7\. Git History

Feature branch: feature/course-registration-validation



Commits:



e77e20f — Fix course capacity and credit validation

2554363 — Add capacity and credit boundary tests

789e1ab — Refactor Maven project and add CI Kubernetes configuration

The feature branch was merged into main. The working tree was clean at final verification.

