# Spring Warmup

The first request in Spring Web can take a lot of time because the Application is not warm yet.
This lib achieves to warmup the application on startup and before it is ready.

This is achieved by a simple dummy HTTP request sent to the application itself.

Other warmup operations can be implemented by implementing the `WarmupOperation` interface and register it as bean.
These beans are automatically collected and its implementations will also be exeucted on startup.
