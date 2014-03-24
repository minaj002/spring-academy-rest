package com.academy.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import com.mongodb.Mongo;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.extract.ITempNaming;
import de.flapdoodle.embed.process.io.IStreamProcessor;
import de.flapdoodle.embed.process.io.NullProcessor;
import de.flapdoodle.embed.process.runtime.Network;

public class AbstractMongoDbTest {
	private static final class BasicExecutableNaming implements ITempNaming {
		@Override
		public String nameFor(String prefix, String postfix) {
			return Command.MongoD.commandName();
		}
	}

	protected static boolean embedded = true;

	private static final String PROCESS_ADDRESS = "localhost";
	private static final int PROCESS_PORT = 27017;

	private static MongodExecutable mongodExecutable = null;
	private static MongodProcess mongodProcess = null;
	private static Mongo mongoClient = null;

	@BeforeClass
	public static void setUp() throws Exception {
		if (embedded) {
			IStreamProcessor stream = new NullProcessor();
			MongodStarter runtime = MongodStarter
					.getInstance(new RuntimeConfigBuilder()
							.defaults(Command.MongoD)
							.processOutput(
									new ProcessOutput(stream, stream, stream))
							.artifactStore(
									new ArtifactStoreBuilder()
											.defaults(Command.MongoD)
											.executableNaming(
													new BasicExecutableNaming())
											.build()).build());
			mongodExecutable = runtime.prepare(new MongodConfigBuilder()
					.version(Version.Main.PRODUCTION)
					.net(new Net(PROCESS_PORT, Network.localhostIsIPv6()))
					.build());

			mongodProcess = mongodExecutable.start();
			mongoClient = new Mongo(PROCESS_ADDRESS, PROCESS_PORT);
		}
	}

	@AfterClass
	public static void tearDown() {
		if (embedded) {
			mongodProcess.stop();
			mongodExecutable.stop();
		}
	}

	protected Mongo getMongoClient() {
		return mongoClient;
	}
}
