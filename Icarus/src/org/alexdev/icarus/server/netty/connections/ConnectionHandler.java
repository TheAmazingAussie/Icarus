package org.alexdev.icarus.server.netty.connections;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.netty.NettyPlayerNetwork;
import org.alexdev.icarus.server.netty.streams.NettyRequest;
import org.alexdev.icarus.util.Util;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ConnectionHandler extends SimpleChannelHandler {

    /* (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#channelOpen(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
     */
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {

        Channel channel = ctx.getChannel();

        Player player = new Player(new NettyPlayerNetwork(channel, channel.getId()));
        channel.setAttachment(player);

        if (Util.getConfiguration().get("Logging", "log.connections", Boolean.class)) {
            Log.info("[" + player.getNetwork().getConnectionId() + "] Connection from " + channel.getRemoteAddress().toString().replace("/", "").split(":")[0]);
        }

    } 

    /* (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#channelClosed(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
        
        Player player = (Player) ctx.getChannel().getAttachment();

        if (Util.getConfiguration().get("Logging", "log.connections", Boolean.class)) {
            Log.info("[" + player.getNetwork().getConnectionId() + "] Disconnection from " + ctx.getChannel().getRemoteAddress().toString().replace("/", "").split(":")[0]);
        }

        player.dispose();

    }

    /* (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

        try {

            Player player = (Player) ctx.getChannel().getAttachment();
            NettyRequest request = (NettyRequest) e.getMessage();

            if (request == null) {
                return;
            }

            if (Util.getConfiguration().get("Logging", "log.received.packets", Boolean.class)) {
                    Log.info("Received: " + request.getMessageId() + " / " + request.getMessageBody());
            }

            if (player != null){
                Icarus.getServer().getMessageHandler().handleRequest(player, request);
            }

        } catch (Exception ex) {
            Log.exception(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        Log.exception(e.getCause());
    }

}
