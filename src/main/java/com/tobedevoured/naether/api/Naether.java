package com.tobedevoured.naether.api;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.deployment.DeploymentException;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.repository.RemoteRepository;

import com.tobedevoured.naether.DependencyException;
import com.tobedevoured.naether.NaetherException;
import com.tobedevoured.naether.URLException;
import com.tobedevoured.naether.deploy.DeployArtifact;
import com.tobedevoured.naether.deploy.DeployException;
import com.tobedevoured.naether.deploy.InstallException;
import com.tobedevoured.naether.maven.Project;
import com.tobedevoured.naether.maven.ProjectException;

public interface Naether {

    /**
     * Clear dependencies
     */
    void clearDependencies();

    /**
     * Clear local BuildArtifacts
     */
    void clearBuildArtifacts();

    /**
     * Add a local Build Artifact manually by String notation, path to the artifact,
     * and path to the POM. 
     * 
     * The Build Artifact will override matchingdependencies found
     * when resolving dependencies.
     * 
     * @param notation String
     * @param path String
     * @param pom String
     */
    void addBuildArtifact(String notation, String path, String pom);

    /**
     * Add a local Build Artifact manually by String notation and path to the artifact.
     * The POM for the artifact is created automatically based on the String notation.
     * 
     * The Build Artifact will override matchingdependencies found
     * when resolving dependencies.
     * 
     * @param notation String
     * @param path String
     * @throws NaetherException if failed to create a POM
     */
    void addBuildArtifact(String notation, String path) throws NaetherException;

    /**
     * Add dependency by String notation with default compile scope
     * 
     * groupId:artifactId:type:version
     * 
     * @param notation String
     */
    void addDependency(String notation);

    /**
     * Add dependency by String notation and Maven scope
     * 
     * groupId:artifactId:type:version
     * 
     * @param notation String
     * @param scope String
     */
    void addDependency(String notation, String scope);

    /**
     * Add {@link Dependency}
     * 
     * @param dependency {@link Dependency}
     */
    void addDependency(Dependency dependency);

    /**
     * Add {@link org.apache.maven.model.Dependency}
     * @param projectDependency {@link org.apache.maven.model.Dependency}
     */
    void addDependency( org.apache.maven.model.Dependency projectDependency);

    /**
     * Add dependences from a Maven POM
     * 
     * @param pomPath String path to POM
     * @throws ProjectException exception
     */
    void addDependencies(String pomPath) throws ProjectException;

    /**
     * Add dependencies from a Maven POM, limited to a List of scopes.
     *
     * @param pomPath String path to POM
     * @param scopes List of scopes
     * @throws ProjectException exception
     */
    void addDependencies(String pomPath, List<String> scopes) throws ProjectException;

    /**
     * Add dependencies from a Maven POM
     * 
     * @param project {@link Project}
     * @throws ProjectException exception
     */
    void addDependencies(Project project) throws ProjectException;

    /**
     * Add dependencies from a Maven POM, limited to a {@link List} of scopes. Adds
     * all Repositories from the Maven Pom.
     * 
     * @param project {@link Project}
     * @param scopes List of dependency scopes
     * @throws ProjectException exception
     */
    void addDependencies(Project project, List<String> scopes) throws ProjectException;

    /**
     * Remove all {@link RemoteRepository}
     */
    void clearRemoteRepositories();

    /**
     * Add a {@link RemoteRepository} by String url
     * 
     * @param url String
     * @throws NaetherException exception
     */
    void addRemoteRepositoryByUrl(String url) throws NaetherException;

    /**
     * Add a {@link RemoteRepository} by String url with String username and
     * password for authentication.
     * 
     * @param url String
     * @param username String
     * @param password String
     * @throws URLException exception
     */
    void addRemoteRepositoryByUrl(String url, String username,String password) throws URLException;

    /**
     * Add a {@link RemoteRepository}
     * 
     * @param id String
     * @param type String
     * @param url String
     */
    void addRemoteRepository(String id, String type, String url);

    /**
     * Add {@link RemoteRepository}
     * 
     * @param remoteRepository {@link RemoteRepository}
     */
    void addRemoteRepository(RemoteRepository remoteRepository);

    /**
     * Set {@link List} of {@link RemoteRepository}
     * 
     * @param remoteRepositories {@link List}
     */
    void setRemoteRepositories(Set<RemoteRepository> remoteRepositories);

    /**
     * Get {@link Set} of {@link RemoteRepository}
     * 
     * @return {@link Set}
     */
    Set<RemoteRepository> getRemoteRepositories();

    /**
     * Get {@link List} of String urls 
     * 
     * @return {@link List}
     */
    List<String> getRemoteRepositoryUrls();

    /**
     * Resolve dependencies and download artifacts
     *
     * @throws URLException exception
     * @throws DependencyException exception
     */
    void resolveDependencies() throws URLException, DependencyException;

    /**
     * Resolve Dependencies
     * 
     * @param downloadArtifacts boolean if artifacts are to be downloaded
     * @throws URLException exception
     * @throws DependencyException exception
     */
    void resolveDependencies(boolean downloadArtifacts)
            throws URLException, DependencyException;

    /**
     * Resolve Dependencies
     * 
     * @param downloadArtifacts if artifacts are to be downloaded
     * @param properties Map of properties
     * @throws URLException exception
     * @throws DependencyException exception
     */
    void resolveDependencies(boolean downloadArtifacts, Map<String, String> properties) throws URLException, DependencyException;

    /**
     * Deploy an Artifact
     * 
     * @param deployArtifact {@link DeployArtifact}
     * @throws DeployException exception
     */
    void deployArtifact(DeployArtifact deployArtifact) throws DeployException;

    /**
     * Install Artifact to local repo. 
     * 
     * If installing a POM, filePath can be null. If install a Jar without a POM, pomPath
     * can be null.
     * 
     * @param notation String
     * @param pomPath String
     * @param filePath String
     * @throws InstallException exception
     */
    void install(String notation, String pomPath, String filePath) throws InstallException;

    /**
     * Get the current dependencies. Returns Collection of resolved Dependencies
     * if {@link #resolveDependencies()} has been called, other returns {@link #getDependencies()}
     *
     * @return Collection
     */
    Collection<Dependency> currentDependencies();
    
    /**
     * Classpath from resolved artifacts
     * 
     * @return String
     */
    String getResolvedClassPath();

    /**
     * {@link List} of {@link Dependency} converted to String notation
     * 
     * @return {@link List}
     */
    Set<String> getDependenciesNotation();

    /**
     * {@link Map} of String notation and the corresponding String file path 
     * 
     * @return {@link Map}
     */
    Map<String, String> getDependenciesPath();

    /**
     * Graph of Dependencies with their transitive Dependencies as children. 
     * 
     * @return Map
     */
    Map<String,Map> getDependenciesGraph();
    
    /**
     * Set local repository path. This is the destination for downloaded
     * metadata and artifacts.
     * 
     * @param repoPath String
     */
    void setLocalRepoPath(String repoPath);

    /**
     * Get local repository path. This is the destination for downloaded
     * metadata and artifacts.
     * 
     * @return String
     */
    String getLocalRepoPath();

    /**
     * Set the {@link List} of {@link Dependency}
     * 
     * @param dependencies {@link List}
     */
    void setDependencies(Set<Dependency> dependencies);

    /**
     * {@link List} of {@link Dependency}
     * 
     * @return {@link List}
     */
    Set<Dependency> getDependencies();

    /**
     * Get List of local artifacts that are used in dependency
     * resolution.
     * 
     * @return List
     */
    List<Artifact> getBuildArtifacts();

    /**
     * Set List for local artifacts that are used in dependency resolution.
     * 
     * @param buildArtifacts List
     */
    void setBuildArtifacts(List<Artifact> buildArtifacts);

    /**
     * Download to the local repository a List of Artifact or String
     * notations.
     * 
     * @param artifactsOrNotations {@link List} of Artifact or String notation
     * @return List
     * @throws NaetherException exception
     */
    @SuppressWarnings("rawtypes")
    List<File> downloadArtifacts(List artifactsOrNotations)
            throws NaetherException;

}