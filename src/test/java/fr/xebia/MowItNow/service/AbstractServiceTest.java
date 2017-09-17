package fr.xebia.MowItNow.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.xebia.MowItNow.config.ApplicationContextTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationContextTest.class })
public abstract class AbstractServiceTest {

}
