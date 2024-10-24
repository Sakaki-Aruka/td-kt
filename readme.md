    /*
     * arguments
     *
     * NEED
     * - "-f FILE_NAME": specify a target .har file.
     *
     * OPTIONAL
     * - "-n": no duplicate download links
     * - "-u": converting a file-name to an uuid-style file-name.
     * - "-s": log silent. (no downloading log)
     * - "-d DIRECTORY": specify a destination directory.
     * - "-i FILE,EXTENSIONS": include image extensions list that is separated with ",".
     * - "-e FILE,EXTENSIONS": exclude image extensions list that is separated with ",".
  
     *
     * NOT_IMPLEMENTED
     *
     * You can write some simple arguments with concatenated single string.
     * e.g.) -nu
     *
     * If you write an argument that needs optional string argument with some simple arguments, you can use that ONLY on the end of parameters.
     * e.g.) -nuf x.har
     *
     * You cannot write consecutive some arguments are require optional string arguments.
     * e.g.) NG: -nufd x.har ./download
     * e.g.) OK: -nuf x.har -d ./download/
     *
     */