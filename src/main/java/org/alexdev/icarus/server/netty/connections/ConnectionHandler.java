package org.alexdev.icarus.server.netty.connections;

import com.mysql.cj.api.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.alexdev.icarus.game.player.Player;

import org.alexdev.icarus.messages.MessageHandler;
import org.alexdev.icarus.server.netty.NettyPlayerNetwork;
import org.alexdev.icarus.server.netty.NettyServer;
import org.alexdev.icarus.server.netty.streams.NettyRequest;
import org.alexdev.icarus.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionHandler extends SimpleChannelInboundHandler<NettyRequest> {

    final private static Logger log = LoggerFactory.getLogger(ConnectionHandler.class);
    private NettyServer server;

    public ConnectionHandler(NettyServer server) {
        this.server = server;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        Player player = new Player(new NettyPlayerNetwork(ctx.channel(), ctx.channel().hashCode()));
        ctx.channel().attr(Player.SESSION_KEY).set(player);

        if (!server.getChannels().add(ctx.channel())) {
            ctx.disconnect();
            return;
        }

        if (Util.getServerConfig().get("Logging", "log.connections", Boolean.class)) {
            log.info("[{}] Connection from {} ", player.getNetwork().getConnectionId(), ctx.channel().remoteAddress().toString().replace("/", "").split(":")[0]);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

        server.getChannels().remove(ctx.channel());
        Player player = ctx.channel().attr(Player.SESSION_KEY).get();

        if (Util.getServerConfig().get("Logging", "log.connections", Boolean.class)) {
            log.info("[{}] Disonnection from {} ", player.getNetwork().getConnectionId(), ctx.channel().remoteAddress().toString().replace("/", "").split(":")[0]);
        }

        player.dispose();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyRequest message) throws Exception {

        try {

            Player player = ctx.channel().attr(Player.SESSION_KEY).get();

            if (message == null) {
                return;
            }

            if (player != null){
                MessageHandler.handleRequest(player, message);
            }

        } catch (Exception ex) {
            log.error("Could not handle message: ", ex);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}