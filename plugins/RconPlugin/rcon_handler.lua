--[[
    The server socket handler for incoming MUS connections.
    
    @author: Quackster
--]]
function listenServer() 

    local server_socket = nil

    plugin:getLogger():info("[Rcon] Attempting to create RCON server on port {}", rcon_port)
    server_socket = luajava.newInstance("java.net.ServerSocket", rcon_port);
    
    plugin:getLogger():info("[Rcon] RCON server listening on port {}", rcon_port)
    
    plugin:runTaskAsynchronously(waitForConnections, { server_socket })
end

--[[
    The function where the socket waits for incoming socket connections
    and listens for data.
    
    @author: Quackster
--]]
function waitForConnections(server_socket)

    while (plugin:isClosed() == false) do
        
        local socket = server_socket:accept()
        plugin:getLogger():info("Accepted connection from {}", socket:toString())

        local incoming_data = util:readToEnd(socket)
        handleRconCommands(incoming_data)
        
        socket:close()
    end
end

--[[
    RCON command handler where it's possible to remote control
    the server.
    
    @author: Quackster
--]]
function handleRconCommands(incoming_data) 

    local rcon_data = util:split(incoming_data, ";")
    
    local password = rcon_data:get(0)
    local command = rcon_data:get(1)
    
    -- Do not continue if the password is incorrect.
    if password ~= rcon_password then
        do return end
    end
    
    plugin:getLogger():info(string.format(" [Rcon] Incoming RCON command: ", command))
    
    -- Find function in global namespace and call it.
    _G[command_handlers[command]](rcon_data)
end

